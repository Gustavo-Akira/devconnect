package br.com.gustavoakira.devconnect.application.usecases.devprofile.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevProfileFindAllQuery {
    int page;
    int size;
}
