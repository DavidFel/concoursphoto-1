(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('SiteUserDialogController', SiteUserDialogController);

    SiteUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SiteUser'];

    function SiteUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SiteUser) {
        var vm = this;

        vm.siteUser = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.siteUser.id !== null) {
                SiteUser.update(vm.siteUser, onSaveSuccess, onSaveError);
            } else {
                SiteUser.save(vm.siteUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('concoursphotoApp:siteUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.birthDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
