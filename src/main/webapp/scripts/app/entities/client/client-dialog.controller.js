'use strict';

angular.module('rrappApp').controller('ClientDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Client', 'Role', 'Restaurant',
        function($scope, $stateParams, $uibModalInstance, entity, Client, Role, Restaurant) {

        $scope.client = entity;
        $scope.roles = Role.query();
        $scope.clients = Client.query();
        $scope.restaurants = Restaurant.query();
        $scope.load = function(id) {
            Client.get({id : id}, function(result) {
                $scope.client = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('rrappApp:clientUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.client.id != null) {
                Client.update($scope.client, onSaveSuccess, onSaveError);
            } else {
                Client.save($scope.client, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate_of_birth = {};

        $scope.datePickerForDate_of_birth.status = {
            opened: false
        };

        $scope.datePickerForDate_of_birthOpen = function($event) {
            $scope.datePickerForDate_of_birth.status.opened = true;
        };
}]);
