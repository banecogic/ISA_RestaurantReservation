'use strict';

angular.module('rrappApp').controller('FriendrequestDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Friendrequest', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Friendrequest, User) {

        $scope.friendrequest = entity;
        $scope.checkFriendsValidation = true;
        $scope.users = User.query();
        $scope.load = function(id) {
            Friendrequest.get({id : id}, function(result) {
                $scope.friendrequest = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('rrappApp:friendrequestUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.friendrequest.id != null) {
                Friendrequest.update($scope.friendrequest, onSaveSuccess, onSaveError);
            } else {
                Friendrequest.save($scope.friendrequest, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForSentDateAndTime = {};

        $scope.datePickerForSentDateAndTime.status = {
            opened: false
        };

        $scope.datePickerForSentDateAndTimeOpen = function($event) {
            $scope.datePickerForSentDateAndTime.status.opened = true;
        };
        $scope.datePickerForAcceptedDateAndTime = {};

        $scope.datePickerForAcceptedDateAndTime.status = {
            opened: false
        };

        $scope.datePickerForAcceptedDateAndTimeOpen = function($event) {
            $scope.datePickerForAcceptedDateAndTime.status.opened = true;
        };
        $scope.checkPotentialFriends = function(){
            if($scope.friendrequest.requester != undefined && $scope.friendrequest.requested != undefined
                && $scope.friendrequest.requester.login == $scope.friendrequest.requested.login){
                $scope.checkFriendsValidation = false;
            } else {
                $scope.checkFriendsValidation = true;
            }
        }
}]);
