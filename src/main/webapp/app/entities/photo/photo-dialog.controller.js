(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('PhotoDialogController', PhotoDialogController);

    PhotoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Photo', 'UserPhotoVote', 'UserPhotoComment', 'SiteUser', 'Concours'];

    function PhotoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Photo, UserPhotoVote, UserPhotoComment, SiteUser, Concours) {
        var vm = this;

        vm.photo = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.userphotovotes = UserPhotoVote.query();
        vm.userphotocomments = UserPhotoComment.query();
        vm.siteusers = SiteUser.query();
        vm.concours = Concours.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.photo.id !== null) {
                Photo.update(vm.photo, onSaveSuccess, onSaveError);
            } else {
                Photo.save(vm.photo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('concoursphotoApp:photoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateCreate = false;

        vm.setImage = function ($file, photo) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        photo.image = base64Data;
                        photo.imageContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
