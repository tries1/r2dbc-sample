package com.example.r2dbc;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PubSub {
    public static void main(String[] args) {
        Publisher<Integer> pub = new Publisher<Integer>() {
            List<Integer> integers = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());

            @Override
            public void subscribe(Subscriber<? super Integer> s) {
                s.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        try {
                            integers
                                    .stream()
                                    .limit(n)
                                    .forEach(i -> {
                                        if (i > 11) {
                                            throw new IllegalArgumentException("integer must be under 11");
                                        }
                                        s.onNext(i);
                                    });
                        } catch (Exception e) {
                            s.onError(e);
                        }

                        s.onComplete();
                    }

                    @Override
                    public void cancel() {
                        System.out.println("cancel");
                    }
                });
            }
        };

        Subscriber<Integer> sub = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(20);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext : " + integer);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError : " + t.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        pub.subscribe(sub);
    }
}
