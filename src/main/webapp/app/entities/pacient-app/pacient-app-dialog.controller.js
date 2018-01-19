(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientAppDialogController', PacientAppDialogController);

    PacientAppDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'PacientApp', 'Pacient'];

    function PacientAppDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, PacientApp, Pacient) {
        var vm = this;

        vm.pacientApp = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.pacients = Pacient.query({filter: 'pacientapp-is-null'});
        $q.all([vm.pacientApp.$promise, vm.pacients.$promise]).then(function() {
            if (!vm.pacientApp.pacient || !vm.pacientApp.pacient.id) {
                return $q.reject();
            }
            return Pacient.get({id : vm.pacientApp.pacient.id}).$promise;
        }).then(function(pacient) {
            vm.pacients.push(pacient);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pacientApp.id !== null) {
                PacientApp.update(vm.pacientApp, onSaveSuccess, onSaveError);
            } else {
                PacientApp.save(vm.pacientApp, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:pacientAppUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.daytime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
