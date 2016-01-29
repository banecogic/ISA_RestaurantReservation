'use strict';

angular.module('rrappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('profil', {
                parent: 'site',
                url: '/profil',
                data: {
                    authorities: [],
                    pageTitle: 'Profil'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/profil/profil.html',
                        controller: 'ProfilController'
                    }
                },
                resolve: {
                    
                }
            })
            .state('profil.dodajprijatelja',  {
                parent: 'profil',
                url: '/addfriend',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$uibModal',  function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/account/profil/profil-addfriend-dialog.html',
                        controller: 'ConfirmFriendRequestController',
                        size: 'md'
                    }).result.then(function(result) {
                        $state.go('profil', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });