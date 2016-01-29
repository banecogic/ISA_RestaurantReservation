'use strict';

angular.module('rrappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('role', {
                parent: 'entity',
                url: '/roles',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN'],
                    pageTitle: 'Roles'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/role/roles.html',
                        controller: 'RoleController'
                    }
                },
                resolve: {
                }
            })
            .state('role.detail', {
                parent: 'entity',
                url: '/role/{id}',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN'],
                    pageTitle: 'Role'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/role/role-detail.html',
                        controller: 'RoleDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Role', function($stateParams, Role) {
                        return Role.get({id : $stateParams.id});
                    }]
                }
            })
            .state('role.new', {
                parent: 'role',
                url: '/new',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/role/role-dialog.html',
                        controller: 'RoleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('role', null, { reload: true });
                    }, function() {
                        $state.go('role');
                    })
                }]
            })
            .state('role.edit', {
                parent: 'role',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/role/role-dialog.html',
                        controller: 'RoleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Role', function(Role) {
                                return Role.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('role', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('role.delete', {
                parent: 'role',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/role/role-delete-dialog.html',
                        controller: 'RoleDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Role', function(Role) {
                                return Role.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('role', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
