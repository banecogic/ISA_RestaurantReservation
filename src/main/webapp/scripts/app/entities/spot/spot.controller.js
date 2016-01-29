'use strict';

angular.module('rrappApp')
    .controller('SpotController', function ($scope, $state, Spot) {

        $scope.spots = [];
        $scope.loadAll = function() {
            Spot.query(function(result) {
               $scope.spots = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.spot = {
                name: null,
                x_position: null,
                y_position: null,
                id: null
            };
        };
    });
