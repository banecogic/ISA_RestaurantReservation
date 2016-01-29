'use strict';

angular.module('rrappApp').controller('RoleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Role', 'Client',
        function($scope, $stateParams, $uibModalInstance, entity, Role, Client) {

        $scope.role = entity;
        $scope.clients = Client.query();
        $scope.load = function(id) {
            Role.get({id : id}, function(result) {
                $scope.role = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('rrappApp:roleUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.role.id != null) {
                Role.update($scope.role, onSaveSuccess, onSaveError);
            } else {
                Role.save($scope.role, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
