package service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqDTO {
    private int qa_num;
    private String type; 
    private String  question; 
    private String answer;
     
}
