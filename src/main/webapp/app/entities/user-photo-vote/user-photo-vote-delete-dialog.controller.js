(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('UserPhotoVoteDeleteController',UserPhotoVoteDeleteController);

    UserPhotoVoteDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserPhotoVote'];

    function UserPhotoVoteDeleteController($uibModalInstance, entity, UserPhotoVote) {
        var vm = this;

        vm.userPhotoVote = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserPhotoVote.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
