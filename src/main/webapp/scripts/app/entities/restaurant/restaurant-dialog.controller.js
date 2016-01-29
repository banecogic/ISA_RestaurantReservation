'use strict';

angular.module('rrappApp').controller('RestaurantDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Restaurant', 'Spot', 'Meal', 'Client',
        function($scope, $stateParams, $uibModalInstance, entity, Restaurant, Spot, Meal, Client) {

        $scope.restaurant = entity;
        $scope.spots = Spot.query();
        $scope.meals = Meal.query();
        $scope.clients = Client.query();
        $scope.load = function(id) {
            Restaurant.get({id : id}, function(result) {
                $scope.restaurant = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('rrappApp:restaurantUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.restaurant.id != null) {
                Restaurant.update($scope.restaurant, onSaveSuccess, onSaveError);
            } else {
                Restaurant.save($scope.restaurant, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
