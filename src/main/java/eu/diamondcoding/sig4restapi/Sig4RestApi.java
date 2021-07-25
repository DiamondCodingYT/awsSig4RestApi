package eu.diamondcoding.sig4restapi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sig4RestApi {

    public static CompletableFuture<String> get(String url) {
        CompletableFuture<String> responseFuture = new CompletableFuture<>();
        getExecutorService().submit(() -> {
            responseFuture.complete("ToDo");
            return null;
        });
        return responseFuture;
    }

    //CachedThreadPool stuff
    private static ExecutorService cachedThreadPool; //var to hold the cachedThreadPool
    private static ExecutorService getExecutorService() {
        if(cachedThreadPool == null) { //if the cachedThreadPool is null
            cachedThreadPool = Executors.newCachedThreadPool(); //created it
        }
        return cachedThreadPool; //anyways return a cached thread pool
    }

}
