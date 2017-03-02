(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('UserPhotoCommentDeleteController',UserPhotoCommentDeleteController);

    UserPhotoCommentDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserPhotoComment'];

    function UserPhotoCommentDeleteController($uibModalInstance, entity, UserPhotoComment) {
        var vm = this;

        vm.userPhotoComment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserPhotoComment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
