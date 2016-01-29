'use strict';

angular.module('rrappApp')
	.controller('MealDeleteController', function($scope, $uibModalInstance, entity, Meal) {

        $scope.meal = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Meal.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
