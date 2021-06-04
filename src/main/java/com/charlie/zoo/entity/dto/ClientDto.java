package com.charlie.zoo.entity.dto;

import com.charlie.zoo.entity.Client;
import com.charlie.zoo.entity.OrderDetails;
import com.charlie.zoo.entity.Phone;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class ClientDto {
    private int id;
    private String name;
    private String phone;

    public static ClientDto convertToDto(Client client){
        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        dto.setName(client.getName());
        String phone = client.getPhones().stream().flatMap(phone1 -> Stream.of(phone1.getPhone())).collect(Collectors.toList()).toString();
        dto.setPhone(phone);
        return dto;
    }

    public static List<ClientDto> convertToListDto(List<Client> details){
        if(details!=null){
            return details.stream().map(ClientDto::convertToDto).collect(Collectors.toList());
        }else {
            return new ArrayList<>();
        }
    }
}
