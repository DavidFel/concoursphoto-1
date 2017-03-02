(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('ConcoursDialogController', ConcoursDialogController);

    ConcoursDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Concours', 'Theme'];

    function ConcoursDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Concours, Theme) {
        var vm = this;

        vm.concours = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.themes = Theme.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.concours.id !== null) {
                Concours.update(vm.concours, onSaveSuccess, onSaveError);
            } else {
                Concours.save(vm.concours, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('concoursphotoApp:concoursUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
