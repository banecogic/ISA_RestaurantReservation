'use strict';

angular.module('rrappApp')
    .controller('ConfirmFriendRequestController', ['User', 'FriendRequestSharedPropertyService', '$http','$scope','$rootScope', '$uibModal', '$uibModalInstance', 'Friendrequest'
                ,function(User, FriendRequestSharedPropertyService, $http, $scope, $rootScope, $uibModal, $uibModalInstance, Friendrequest) {
        
        var friendRequest = {};
        $scope.friendRequest = friendRequest;
        $scope.friendToRequest = FriendRequestSharedPropertyService.getProperty();
        $scope.friendRequest.isAccepted = 0;
        User.get({login: $rootScope.account.login}, function(result) {
            $scope.friendRequest.requester = result;
        });
        
        var clear = function() {
            $uibModalInstance.close();
        };
        $scope.clear = clear;
        $scope.confirmRequest = function (requester_id, requested_id) {
            $scope.friendRequest.requested = $scope.friendToRequest.originalObject;
            $scope.friendRequest.sentDateAndTime = new Date();
            $scope.friendRequest.acceptedDateAndTime = null;
            Friendrequest.save($scope.friendRequest, onSaveSuccess, onSaveError,
                function () {
            });
            
        };
        var onSaveSuccess = function(){
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/app/account/profil/profil-addedfriend-alert-success.html',
                controller: 'ConfirmFriendRequestController',
                size: 'lg',
            });
            modalInstance.result.then(function (selectedItem) {
                $uibModalInstance.close();
                
            }, function () {
                $uibModalInstance.close();
            });
        };
        var onSaveError = function(result){
            $scope.message = result.headers("Message");
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/app/account/profil/profil-addedfriend-alert-error.html',
                controller: 'ConfirmFriendRequestController',
                size: 'lg',
                scope: $scope
            });
            modalInstance.result.then(function (selectedItem) {
                $uibModalInstance.close();
                
            }, function () {
                $uibModalInstance.close();
            });
        };
        $scope.uredu = function(result){
            clear();
        };
    }]);