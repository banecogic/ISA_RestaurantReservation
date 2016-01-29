'use strict';

angular.module('rrappApp')
    .controller('ClientDetailController', function ($scope, $rootScope, $stateParams, entity, Client, Role, Restaurant) {
        $scope.client = entity;
        $scope.load = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
            });
        };
        var unsubscribe = $rootScope.$on('rrappApp:clientUpdate', function(event, result) {
            $scope.client = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
