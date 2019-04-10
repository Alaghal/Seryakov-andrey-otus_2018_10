package ru.otus.L14.services.commands;

import ru.otus.l10.orm.users.MyUser;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GetMyUsersCommand<T> implements Command {
    private final Map<String,Object>  poolValuesOfMessageSystem;
    private final String LIST_MY_USERS = "listOfMyUserClass";


    public GetMyUsersCommand(Map<String, Object> poolValuesOfMessageSystem) {
        this.poolValuesOfMessageSystem = poolValuesOfMessageSystem;
    }

    @Override
    public T exec() throws ExecutionException, InterruptedException {
        final CompletableFuture<List<MyUser>> future = CompletableFuture.supplyAsync( ()->{
            boolean messageResponseRecived = false;
            List<MyUser> users= null;
            while (!messageResponseRecived){
                if( poolValuesOfMessageSystem.containsKey(LIST_MY_USERS)){
                    messageResponseRecived=true;
                    if(poolValuesOfMessageSystem.get(LIST_MY_USERS) != null) {
                        users = (List<MyUser>) poolValuesOfMessageSystem.get( LIST_MY_USERS );
                    }

                }

            }
            return users;
        } );
        return (T) future.get();
    }
}
