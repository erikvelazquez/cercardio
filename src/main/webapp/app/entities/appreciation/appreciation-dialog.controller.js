(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('AppreciationDialogController', AppreciationDialogController);

    AppreciationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Appreciation', 'Pacient', 'Medic'];

    function AppreciationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Appreciation, Pacient, Medic) {
        var vm = this;

        vm.appreciation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.pacients = Pacient.query();
        vm.medics = Medic.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.appreciation.id !== null) {
                Appreciation.update(vm.appreciation, onSaveSuccess, onSaveError);
            } else {
                Appreciation.save(vm.appreciation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:appreciationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdat = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
