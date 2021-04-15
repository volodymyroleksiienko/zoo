package com.charlie.zoo.utils.haustier;

import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class HaustierXmlParser {
    public static void parse(MultipartFile multipartFile){
        try {
        JAXBContext jc = JAXBContext.newInstance(Rss.class);

        File file = new File("/feed.xml");

//        multipartFile.transferTo(file);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Rss fosterHome = (Rss) unmarshaller.unmarshal(file);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(fosterHome, System.out);

            System.out.println(fosterHome.getChannel().getItemList().size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
