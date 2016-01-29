'use strict';

angular.module('rrappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('friendrequest', {
                parent: 'entity',
                url: '/friendrequests',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN'],
                    pageTitle: 'Friendrequests'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/friendrequest/friendrequests.html',
                        controller: 'FriendrequestController'
                    }
                },
                resolve: {
                }
            })
            .state('friendrequest.detail', {
                parent: 'entity',
                url: '/friendrequest/{id}',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN'],
                    pageTitle: 'Friendrequest'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/friendrequest/friendrequest-detail.html',
                        controller: 'FriendrequestDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Friendrequest', function($stateParams, Friendrequest) {
                        return Friendrequest.get({id : $stateParams.id});
                    }]
                }
            })
            .state('friendrequest.new', {
                parent: 'friendrequest',
                url: '/new',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/friendrequest/friendrequest-dialog.html',
                        controller: 'FriendrequestDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    sentDateAndTime: null,
                                    isAccepted: false,
                                    acceptedDateAndTime: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('friendrequest', null, { reload: true });
                    }, function() {
                        $state.go('friendrequest');
                    })
                }]
            })
            .state('friendrequest.edit', {
                parent: 'friendrequest',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/friendrequest/friendrequest-dialog.html',
                        controller: 'FriendrequestDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Friendrequest', function(Friendrequest) {
                                return Friendrequest.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('friendrequest', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('friendrequest.delete', {
                parent: 'friendrequest',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_MANAGER', 'ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/friendrequest/friendrequest-delete-dialog.html',
                        controller: 'FriendrequestDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Friendrequest', function(Friendrequest) {
                                return Friendrequest.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('friendrequest', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
