package com.example.product;

import com.example.product.handler.ProductHandler;
import com.example.product.service.ProductService;
import com.example.product.service.impl.ProductServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ratpack.error.ClientErrorHandler;
import ratpack.guice.Guice;
import ratpack.handling.Context;
import ratpack.server.RatpackServer;

public class App {
    public static void main(String[] args) throws Exception {
        RatpackServer.start(server -> server
                .registry(Guice.registry(bindings -> bindings
                        .bind(ProductService.class, ProductServiceImpl.class)
                ))
                .handlers(chain -> {
                    ProductHandler.configure(chain, chain.getRegistry().get(ProductService.class));
                })
        );
    }
}
