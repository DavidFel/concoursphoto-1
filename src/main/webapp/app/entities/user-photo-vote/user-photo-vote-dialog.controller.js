(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('UserPhotoVoteDialogController', UserPhotoVoteDialogController);

    UserPhotoVoteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserPhotoVote', 'SiteUser', 'Photo'];

    function UserPhotoVoteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserPhotoVote, SiteUser, Photo) {
        var vm = this;

        vm.userPhotoVote = entity;
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
            if (vm.userPhotoVote.id !== null) {
                UserPhotoVote.update(vm.userPhotoVote, onSaveSuccess, onSaveError);
            } else {
                UserPhotoVote.save(vm.userPhotoVote, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('concoursphotoApp:userPhotoVoteUpdate', result);
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
