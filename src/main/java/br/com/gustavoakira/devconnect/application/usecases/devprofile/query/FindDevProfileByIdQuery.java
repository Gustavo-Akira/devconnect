package br.com.gustavoakira.devconnect.application.usecases.devprofile.query;

public class FindDevProfileByIdQuery {
    private final Long id;

    public FindDevProfileByIdQuery(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
