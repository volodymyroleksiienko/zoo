package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Image;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.jpa.ImageJPA;
import com.charlie.zoo.service.ImageService;
import com.charlie.zoo.service.ProducerService;
import com.charlie.zoo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

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
                image.setImg(multipartFile.getBytes());
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
        if (fileList.size()>0){
            if(fileList.get(0)!=null){
                Image main = update(fileList.get(0),findMainByProductId(product.getId()));
                main.setMain(true);
                imageJPA.save(main);
            }
        }
        List<Image> images = findAllByProductId(product.getId());
        for(int i=1;i<fileList.size();i++){
            if((images.size()-i)>0){
                update(fileList.get(i),images.get(i));
            }else{
                Image newImage = save(fileList.get(i));
                if(newImage!=null) {
                    newImage.setProduct(product);
                    imageJPA.save(newImage);
                }
            }
        }
        return images;
    }

    @Override
    public Image update(MultipartFile multipartFile, Image imageDB) {
        if(imageDB!=null && imageDB.getId()>0 && multipartFile!=null && multipartFile.getSize()>0){
            try {
                imageDB.setImg(multipartFile.getBytes());
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
    public Image findMainByProductId(int id) {
        Image image = imageJPA.findFirstByProductIdAndMainTrue(id);
        return (image==null)? new Image():image;
    }

    @Override
    public List<Image> findAllByProductId(int id) {
        return imageJPA.findAllByProductId(id);
    }

    @Override
    public Image findById(int id) {
        return imageJPA.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int id) {
        imageJPA.deleteById(id);
    }
}
