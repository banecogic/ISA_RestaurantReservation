'use strict';

angular.module('rrappApp')
    .controller('SpotDetailController', function ($scope, $rootScope, $stateParams, entity, Spot, Restaurant) {
        $scope.spot = entity;
        $scope.load = function (id) {
            Spot.get({id: id}, function(result) {
                $scope.spot = result;
            });
        };
        var unsubscribe = $rootScope.$on('rrappApp:spotUpdate', function(event, result) {
            $scope.spot = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
