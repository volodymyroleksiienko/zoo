package com.charlie.zoo.utils.haustier;

import com.charlie.zoo.entity.Image;
import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.service.ProducerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.util.resources.cldr.ga.TimeZoneNames_ga;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
@AllArgsConstructor
public class HaustierXmlParser {
    private final ProducerService producerService;

    public List<Product> parseManager(MultipartFile multipartFile){
        Rss rss = parse(multipartFile);
        Map<String,List<Item>> map = group(rss.getChannel().getItemList());
        return convertToProduct(map);
    }

    public Rss parse(MultipartFile multipartFile){
        try {
        JAXBContext jc = JAXBContext.newInstance(Rss.class);


        File file = new File(System.getProperty("user.dir")+"/haustier.xml");
        multipartFile.transferTo(file);


        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Rss rss = (Rss) unmarshaller.unmarshal(file);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(rss, System.out);

        return rss;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String,List<Item>> group(List<Item> items){
        Map<String,List<Item>> map = new HashMap<>();

        Set<String> keys = new TreeSet<>();
        for(Item item:items){
            String str = item.getMpn();
            int cutLength = str.lastIndexOf("-")+1;
            String key = str.substring(0,cutLength);
            keys.add(key);
        }
        for(String k:keys){
            List<Item> groupItems = new ArrayList<>();
            for (Item item:items){
                if(item.getMpn().contains(k)){
                    groupItems.add(item);
                }
            }
            map.put(k,groupItems);
        }
        return map;
    }

    public  List<Product> convertToProduct(Map<String,List<Item>> map){
        List<Product> productList = new ArrayList<>();
        for(String key: map.keySet()){
            List<Item> itemList = map.get(key);
            if(itemList.size()==0){
                continue;
            }
            Product product = initProduct(itemList.get(0));
            List<PackageType> packageTypes = new ArrayList<>();
            for(Item item: itemList){
                PackageType type = new PackageType();
                type.setProducerFactoryId(item.getMpn());
                type.setPackType(item.getMpn().replace(key,""));
                type.setProduct(product);
                type.setPackSize(new BigDecimal(0));
                type.setPrice(new BigDecimal(item.getPrice()
                        .replace("UAH","")
                        .replace(" ","")));
                if(item.getSalePrice()!=null) {
                    type.setNewPrice(new BigDecimal(item.getSalePrice()
                            .replace("UAH", "")
                            .replace(" ", "")));
                }


                packageTypes.add(type);
            }
            product.setPackageType(packageTypes);
            productList.add(product);
        }
        return productList;
    }


    public Product initProduct(Item item){
        Product product = new Product();

        product.setName("EDIT "+item.getTitle());
        product.setDetails(item.getDescription());
        product.setShortDescription(item.getDescription());

        List<Image> imageList = new ArrayList<>();

        Image image = new Image();
        image.setMain(true);
        image.setProduct(product);
        image.setImg(recoverImageFromUrl(item.getImageLink()));
        image.setImgType("image/jpeg");
        image.setImgName(new Date().toString()+".jpg");
        imageList.add(image);

        System.out.println(item.getAddImageLink().size());
        for(int i=0;i<item.getAddImageLink().size() && i<3;i++){
            String link=item.getAddImageLink().get(i);
            System.out.println(link);
            Image img = new Image();
            img.setProduct(product);
            img.setImg(recoverImageFromUrl(link));
            img.setImgType("image/jpeg");
            img.setImgName(new Date().toString()+".jpg");
            imageList.add(img);
        }
        product.setImages(imageList);
        product.setProducer(
                producerService.findByName(item.getBrand())
        );
        return product;
    }

    public  List<Product> convertToProduct(Rss rss)  {
        List<Product> productList = new ArrayList<>();
        List<Item> itemList = rss.getChannel().getItemList();

        for(Item item:itemList){
            Product product = new Product();

            product.setName("EDIT "+item.getTitle());
            product.setDetails(item.getDescription());
            product.setShortDescription(item.getDescription());

            PackageType type = new PackageType();
            type.setPackType("шт");
            type.setProduct(product);
            type.setPackSize(new BigDecimal(1));
            type.setPrice(new BigDecimal(item.getPrice()
                    .replace("UAH","")
                    .replace(" ","")));
            if(item.getSalePrice()!=null) {
                type.setNewPrice(new BigDecimal(item.getSalePrice()
                        .replace("UAH", "")
                        .replace(" ", "")));
            }

            product.setPackageType(Collections.singletonList(type));

            List<Image> imageList = new ArrayList<>();

            Image image = new Image();
            image.setMain(true);
            image.setProduct(product);
            image.setImg(recoverImageFromUrl(item.getImageLink()));
            image.setImgType("image/jpeg");
            image.setImgName(new Date().toString()+".jpg");
            imageList.add(image);

            System.out.println(item.getAddImageLink().size());
            for(int i=0;i<item.getAddImageLink().size() && i<3;i++){
                String link=item.getAddImageLink().get(i);
                System.out.println(link);
                Image img = new Image();
                img.setProduct(product);
                img.setImg(recoverImageFromUrl(link));
                img.setImgType("image/jpeg");
                img.setImgName(new Date().toString()+".jpg");
                imageList.add(img);
            }
            product.setImages(imageList);
            product.setProducer(
                    producerService.findByName(item.getBrand())
            );

            productList.add(product);
        }
        return productList;
    }


    public byte[] recoverImageFromUrl(String urlText)  {
        URL url = null;
        try {
            url = new URL(urlText);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int n = 0;
            byte [] buffer = new byte[ 1024 ];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return output.toByteArray();
    }
}
