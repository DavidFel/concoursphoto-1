(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('UserPhotoCommentDialogController', UserPhotoCommentDialogController);

    UserPhotoCommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserPhotoComment', 'SiteUser', 'Photo'];

    function UserPhotoCommentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserPhotoComment, SiteUser, Photo) {
        var vm = this;

        vm.userPhotoComment = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.siteusers = SiteUser.query();
        vm.photos = Photo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userPhotoComment.id !== null) {
                UserPhotoComment.update(vm.userPhotoComment, onSaveSuccess, onSaveError);
            } else {
                UserPhotoComment.save(vm.userPhotoComment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('concoursphotoApp:userPhotoCommentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
