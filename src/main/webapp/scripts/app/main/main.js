'use strict';

angular.module('rrappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/main.html',
                        controller: 'MainController'
                    }
                },
                resolve: {
                    
                }
            })
            .state('restorani', {
                parent: 'site',
                url: '/restaurants',
                data: {
                    authorities: [],
                    pageTitle: 'Restaurants'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/restaurant/restaurants.html',
                        controller: 'RestaurantController'
                    }
                },
                resolve: {
                }
            })
    });
