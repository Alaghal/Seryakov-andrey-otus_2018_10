package ru.otus.L14.services.commands;

import ru.otus.l11.entity.MyUser;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GetUserOfLoginCommand<T> implements Command {
    private  final Map<String,Object>  poolUserOfMessageSystem;
    private final String login;

    public GetUserOfLoginCommand(Map<String, Object> poolUserOfMessageSystem, String login) {
        this.poolUserOfMessageSystem = poolUserOfMessageSystem;
        this.login = login;
    }


    @Override
    public T exec() throws ExecutionException, InterruptedException {
        final CompletableFuture<MyUser> future = CompletableFuture.supplyAsync( ()->{
            boolean messageResponseReceived = false;
            MyUser user = null;
            while (!messageResponseReceived){
                if( poolUserOfMessageSystem.containsKey(login)){
                    messageResponseReceived=true;
                    if(poolUserOfMessageSystem.get( login ) != null) {
                        user = (MyUser) poolUserOfMessageSystem.get( login );
                    }

                }

            }
            return user;
        } );
        return (T) future.get();
    }
}
