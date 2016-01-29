'use strict';

angular.module('rrappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('spot', {
                parent: 'entity',
                url: '/spots',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN'],
                    pageTitle: 'Spots'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/spot/spots.html',
                        controller: 'SpotController'
                    }
                },
                resolve: {
                }
            })
            .state('spot.detail', {
                parent: 'entity',
                url: '/spot/{id}',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN'],
                    pageTitle: 'Spot'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/spot/spot-detail.html',
                        controller: 'SpotDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Spot', function($stateParams, Spot) {
                        return Spot.get({id : $stateParams.id});
                    }]
                }
            })
            .state('spot.new', {
                parent: 'spot',
                url: '/new',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/spot/spot-dialog.html',
                        controller: 'SpotDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    x_position: null,
                                    y_position: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('spot', null, { reload: true });
                    }, function() {
                        $state.go('spot');
                    })
                }]
            })
            .state('spot.edit', {
                parent: 'spot',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/spot/spot-dialog.html',
                        controller: 'SpotDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Spot', function(Spot) {
                                return Spot.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('spot', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('spot.delete', {
                parent: 'spot',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/spot/spot-delete-dialog.html',
                        controller: 'SpotDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Spot', function(Spot) {
                                return Spot.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('spot', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
