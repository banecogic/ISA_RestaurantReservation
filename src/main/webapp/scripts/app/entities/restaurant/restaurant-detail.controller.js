'use strict';

angular.module('rrappApp')
    .controller('RestaurantDetailController', function ($scope, $rootScope, $stateParams, entity, Restaurant, Spot, Meal, Client) {
        $scope.restaurant = entity;
        $scope.load = function (id) {
            Restaurant.get({id: id}, function(result) {
                $scope.restaurant = result;
            });
        };
        var unsubscribe = $rootScope.$on('rrappApp:restaurantUpdate', function(event, result) {
            $scope.restaurant = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
