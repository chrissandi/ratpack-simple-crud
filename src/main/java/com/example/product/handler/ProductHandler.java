package com.example.product.handler;

import com.example.product.model.Product;
import com.example.product.service.ProductService;
import ratpack.handling.Chain;
import ratpack.jackson.Jackson;

public class ProductHandler{
    public static void configure(Chain chain, ProductService productService) throws Exception {
        //get the path
        chain.path("product", ctx -> {
            //separate by http method
            ctx.byMethod(method -> method
                    .get(() -> {
                        productService.getAllProducts()
                                .then(products -> {
                                    if (!products.isEmpty()) {
                                        ctx.render(Jackson.json(products));
                                    } else {
                                        ctx.clientError(404);
                                    }});
                    })
                    .post(() -> {
                        ctx.parse(Jackson.fromJson(Product.class))
                                .flatMap(product -> productService.createProduct(product))
                                .onError(throwable -> {
                                    ctx.error(throwable);
                                })
                                .then(createdProduct -> {
                                    if (createdProduct != null) {
                                        ctx.render(Jackson.json(createdProduct));
                                    } else {
                                        ctx.render("Product created successfully");
                                    }
                                });
                    })
            );
        });
        chain.path("product/:id", ctx -> {
            ctx.byMethod(method -> method
                    .get(() -> {
                        Long id = Long.parseLong(ctx.getPathTokens().get("id"));
                        productService.getProductById(id)
                                .then(product -> {
                                    if (product!=null) {
                                        ctx.render(Jackson.json(product));
                                    } else {
                                        ctx.clientError(404);
                                    }}
                                );

                    })
                    .put(() -> {
                        Long id = Long.parseLong(ctx.getPathTokens().get("id"));
                        ctx.parse(Jackson.fromJson(Product.class))
                                .then(updatedProduct -> productService.updateProduct(id, updatedProduct)
                                        .then(product -> {
                                            if (product != null) {
                                                ctx.render(Jackson.json(product));
                                            } else {
                                                ctx.clientError(404);
                                            }
                                        })
                                );
                    })
                    .delete(() -> {
                        Long id = Long.parseLong(ctx.getPathTokens().get("id"));
                        productService.deleteProduct(id)
                                .onError(throwable -> ctx.clientError(404))
                                .then(v -> ctx.render("Product deleted successfully"));
                    })
            );
        });
    }
}
