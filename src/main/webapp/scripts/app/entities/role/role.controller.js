'use strict';

angular.module('rrappApp')
    .controller('RoleController', function ($scope, $state, Role) {

        $scope.roles = [];
        $scope.loadAll = function() {
            Role.query(function(result) {
               $scope.roles = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.role = {
                name: null,
                id: null
            };
        };
    });
