'use strict';

angular.module('rrappApp').controller('SpotDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Spot', 'Restaurant',
        function($scope, $stateParams, $uibModalInstance, entity, Spot, Restaurant) {

        $scope.spot = entity;
        $scope.restaurants = Restaurant.query();
        $scope.load = function(id) {
            Spot.get({id : id}, function(result) {
                $scope.spot = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('rrappApp:spotUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.spot.id != null) {
                Spot.update($scope.spot, onSaveSuccess, onSaveError);
            } else {
                Spot.save($scope.spot, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
