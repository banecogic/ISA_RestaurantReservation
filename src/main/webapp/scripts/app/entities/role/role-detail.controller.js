'use strict';

angular.module('rrappApp')
    .controller('RoleDetailController', function ($scope, $rootScope, $stateParams, entity, Role, Client) {
        $scope.role = entity;
        $scope.load = function (id) {
            Role.get({id: id}, function(result) {
                $scope.role = result;
            });
        };
        var unsubscribe = $rootScope.$on('rrappApp:roleUpdate', function(event, result) {
            $scope.role = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
