'use strict';

angular.module('rrappApp')
	.controller('RestaurantDeleteController', function($scope, $uibModalInstance, entity, Restaurant) {

        $scope.restaurant = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Restaurant.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
