'use strict';

angular.module('rrappApp')
	.controller('FriendrequestDeleteController', function($scope, $uibModalInstance, entity, Friendrequest) {

        $scope.friendrequest = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Friendrequest.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
