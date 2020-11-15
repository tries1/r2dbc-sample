package com.example.r2dbc;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PubSubSample {
    public static void main(String[] args) {
        List<Integer> integers = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());

        Publisher<Integer> pub = new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> s) {
                s.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        try {
                            integers
                                    .stream()
                                    .forEach(i -> {
                                        if (i > 5) {
                                            throw new IllegalArgumentException("integer must be under 5");
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
                s.request(2);
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
