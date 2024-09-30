package com.pw.routemanagement1.beans;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.apache.camel.spi.RouteController;

public class RouteProcessor implements Processor{

    // Enum representing the route actions
    private enum RouteAction {
        START,
        STOP,
        SUSPEND,
        RESUME;
    }

    // Define an EnumMap to map actions to the enum
    private final Map<RouteAction, BiConsumer<RouteController, String>> actionMap = new EnumMap<>(RouteAction.class);

    public RouteProcessor() {
        // Initialize the action map
        actionMap.put(RouteAction.START, (arg0, arg1) -> {
            try {
                arg0.startRoute(arg1);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        actionMap.put(RouteAction.STOP, (arg0, arg1) -> {
            try {
                arg0.stopRoute(arg1);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        actionMap.put(RouteAction.SUSPEND, (arg0, arg1) -> {
            try {
                arg0.suspendRoute(arg1);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        actionMap.put(RouteAction.RESUME, (arg0, arg1) -> {
            try {
                arg0.resumeRoute(arg1);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    // Method to execute the route action
    @Override
    public void process(Exchange exchange) throws Exception {
        String routeId = exchange.getIn().getHeader("routeId", String.class);
        String action = exchange.getIn().getHeader("action", String.class);
        Route route = exchange.getContext().getRoute(routeId);

        if (route != null) {
            try {
                BiConsumer<RouteController, String> actionHandler = actionMap.get(RouteAction.valueOf(action.toUpperCase()));
                if (actionHandler != null) {
                    actionHandler.accept(exchange.getContext().getRouteController(), routeId);
                    exchange.getMessage().setBody("Route " + action.toLowerCase() + "ed successfully");
                }
            } catch (IllegalArgumentException e) {
                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
                exchange.getMessage().setBody("Invalid action. Supported actions are: start, stop, suspend, resume.");
            }
        } else {
            exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
            exchange.getMessage().setBody("Route not found");
        }
    }

}
