'use strict';

angular.module('rrappApp')
    .controller('MealDetailController', function ($scope, $rootScope, $stateParams, entity, Meal, Restaurant) {
        $scope.meal = entity;
        $scope.load = function (id) {
            Meal.get({id: id}, function(result) {
                $scope.meal = result;
            });
        };
        var unsubscribe = $rootScope.$on('rrappApp:mealUpdate', function(event, result) {
            $scope.meal = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
