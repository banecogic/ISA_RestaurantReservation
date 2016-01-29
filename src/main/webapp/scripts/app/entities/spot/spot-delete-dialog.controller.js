'use strict';

angular.module('rrappApp')
	.controller('SpotDeleteController', function($scope, $uibModalInstance, entity, Spot) {

        $scope.spot = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Spot.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
