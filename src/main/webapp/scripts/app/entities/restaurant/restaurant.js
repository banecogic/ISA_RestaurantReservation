'use strict';

angular.module('rrappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('restaurant', {
                parent: 'entity',
                url: '/restaurant',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN'],
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
            .state('restaurant.detail', {
                parent: 'entity',
                url: '/restaurant/{id}',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN'],
                    pageTitle: 'Restaurant'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/restaurant/restaurant-detail.html',
                        controller: 'RestaurantDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Restaurant', function($stateParams, Restaurant) {
                        return Restaurant.get({id : $stateParams.id});
                    }]
                }
            })
            .state('restaurant.new', {
                parent: 'restaurant',
                url: '/new',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/restaurant/restaurant-dialog.html',
                        controller: 'RestaurantDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    map_x_coordinate: null,
                                    map_y_coordinate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('restaurant', null, { reload: true });
                    }, function() {
                        $state.go('restaurant');
                    })
                }]
            })
            .state('restaurant.edit', {
                parent: 'restaurant',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/restaurant/restaurant-dialog.html',
                        controller: 'RestaurantDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Restaurant', function(Restaurant) {
                                return Restaurant.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('restaurant', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('restaurant.delete', {
                parent: 'restaurant',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/restaurant/restaurant-delete-dialog.html',
                        controller: 'RestaurantDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Restaurant', function(Restaurant) {
                                return Restaurant.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('restaurant', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
