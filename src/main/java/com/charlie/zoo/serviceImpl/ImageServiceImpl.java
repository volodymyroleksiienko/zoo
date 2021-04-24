package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Image;
import com.charlie.zoo.entity.Producer;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.jpa.ImageJPA;
import com.charlie.zoo.service.ImageService;
import com.charlie.zoo.service.ProductService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements  ImageService {

    private ImageJPA imageJPA;
    private ProductService productService;

    public ImageServiceImpl(ImageJPA imageJPA,@Lazy ProductService productService) {
        this.imageJPA = imageJPA;
        this.productService = productService;
    }

    @Override
    public Image save(MultipartFile multipartFile) {
        Image image = null;
        if(multipartFile!=null && multipartFile.getSize()>0){
            try {
                image=new Image();
                image.setImg(compressImage(multipartFile));
//                image.setImg(multipartFile.getBytes());
                image.setImgName(multipartFile.getOriginalFilename());
                image.setImgType(multipartFile.getContentType());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        if(image!=null) {
            return imageJPA.save(image);
        }else {
            return null;
        }
    }

    @Override
    public Image save(Image image) {
        return imageJPA.save(image);
    }

    public byte[] compressImage(MultipartFile multipartFile) throws IOException {
        BufferedImage image =Thumbnails.of(multipartFile.getInputStream())
                .height(600)
                .width(600)
                .outputQuality(0.5)
                .asBufferedImage();
        return toByteArray(image,"png");
    }

    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        return baos.toByteArray();

    }


    @Override
    public List<Image> save(List<MultipartFile> fileList, Product product) {
        List<Image> images = new ArrayList<>();
        for(int i=0;i<fileList.size();i++){
            Image image = save(fileList.get(i));
            if(image!=null && image.getImgName()!=null && !image.getImgName().isEmpty()){
                if(i==0) {
                    image.setMain(true);
                }
                image.setProduct(product);
                images.add(image);
            }
        }
        return imageJPA.saveAll(images);
    }

    @Override
    public List<Image> update(List<MultipartFile> fileList, Product product) {
        if(fileList.size()>0 && fileList.get(0)!=null){
            Image main = findMainByProductId(product.getId());
            Image image = update(fileList.get(0),main);
            image.setProduct(product);
            image.setMain(true);
            imageJPA.save(image);
        }
        List<Image> images = imageJPA.findByProductIdAndMainFalse(product.getId());
        for(int i=1;i<fileList.size();i++){
            if(fileList.get(i)!=null){
                Image imageDB;
                if(i<=images.size()){
                    imageDB=images.get(i-1);
                }else {
                    imageDB = null;
                }
                Image image = update(fileList.get(i),imageDB);
                image.setProduct(product);
                imageJPA.save(image);
            }
        }
        System.out.println("end of update");
        return images;
    }

    @Override
    public Image update(MultipartFile multipartFile, Image imageDB) {
        if(imageDB==null){
            imageDB = new Image();
        }
        if(multipartFile!=null && multipartFile.getSize()>0){
            try {
                imageDB.setImg(compressImage(multipartFile));
//                imageDB.setImg(multipartFile.getBytes());
                imageDB.setImgName(multipartFile.getOriginalFilename());
                imageDB.setImgType(multipartFile.getContentType());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return imageJPA.save(imageDB);
        }
        return null;
    }

    @Override
    public Image update(MultipartFile multipartFile, Producer producer) {
        Image imageDB;
        if(producer.getImage()==null){
             imageDB = new Image();
        }else {
            imageDB = producer.getImage();
        }
        imageDB.setProducer(producer);
        if(multipartFile!=null && multipartFile.getSize()>0){
            try {
                imageDB.setImg(multipartFile.getBytes());
                imageDB.setImgName(multipartFile.getOriginalFilename());
                imageDB.setImgType(multipartFile.getContentType());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return imageJPA.save(imageDB);
    }

    @Override
    public Image findMainByProductId(int id) {
        Image image = imageJPA.findFirstByProductIdAndMainTrue(id);
        return (image==null)? new Image():image;
    }

    @Override
    public List<Image> findByProductIdAndMainFalse(int id) {
        return imageJPA.findByProductIdAndMainFalse(id);
    }

    @Override
    public List<Image> findAllByProductId(int id) {
        return imageJPA.findAllByProductId(id).stream()
                .sorted(Comparator.comparing(Image::isMain,Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public Image findById(int id) {
        return imageJPA.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int id) {
        if(id>0){
            if(findById(id)!=null) {
                imageJPA.deleteById(id);
            }
        }
    }
}
