package ru;

import lombok.Data;

import javax.persistence.*;
@Data
public class Cars {
    private int code;
    private int number;
    private int places;
    private Owners codeOwner;
    private Autopark codeAutopark;
}
