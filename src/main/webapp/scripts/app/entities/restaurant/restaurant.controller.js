'use strict';

angular.module('rrappApp')
    .controller('RestaurantController', function ($scope, $state, Restaurant) {

        $scope.restaurants = [];
        $scope.loadAll = function() {
            Restaurant.query(function(result) {
               $scope.restaurants = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.restaurant = {
                name: null,
                description: null,
                map_x_coordinate: null,
                map_y_coordinate: null,
                id: null
            };
        };
    });
