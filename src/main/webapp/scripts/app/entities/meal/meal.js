'use strict';

angular.module('rrappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('meal', {
                parent: 'entity',
                url: '/meals',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN'],
                    pageTitle: 'Meals'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/meal/meals.html',
                        controller: 'MealController'
                    }
                },
                resolve: {
                }
            })
            .state('meal.detail', {
                parent: 'entity',
                url: '/meal/{id}',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN'],
                    pageTitle: 'Meal'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/meal/meal-detail.html',
                        controller: 'MealDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Meal', function($stateParams, Meal) {
                        return Meal.get({id : $stateParams.id});
                    }]
                }
            })
            .state('meal.new', {
                parent: 'meal',
                url: '/new',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/meal/meal-dialog.html',
                        controller: 'MealDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    price: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('meal', null, { reload: true });
                    }, function() {
                        $state.go('meal');
                    })
                }]
            })
            .state('meal.edit', {
                parent: 'meal',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/meal/meal-dialog.html',
                        controller: 'MealDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Meal', function(Meal) {
                                return Meal.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('meal', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('meal.delete', {
                parent: 'meal',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/meal/meal-delete-dialog.html',
                        controller: 'MealDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Meal', function(Meal) {
                                return Meal.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('meal', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
