package br.com.gustavoakira.devconnect.application.usecases.devprofile.command;

public class DeleteDevProfileCommand {
    private Long id;

    public DeleteDevProfileCommand(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
