(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('ThemeDialogController', ThemeDialogController);

    ThemeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Theme', 'Concours'];

    function ThemeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Theme, Concours) {
        var vm = this;

        vm.theme = entity;
        vm.clear = clear;
        vm.save = save;
        vm.concours = Concours.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.theme.id !== null) {
                Theme.update(vm.theme, onSaveSuccess, onSaveError);
            } else {
                Theme.save(vm.theme, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('concoursphotoApp:themeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
