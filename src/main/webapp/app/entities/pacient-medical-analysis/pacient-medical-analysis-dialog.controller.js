(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientMedicalAnalysisDialogController', PacientMedicalAnalysisDialogController);

    PacientMedicalAnalysisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'PacientMedicalAnalysis', 'Medic', 'Pacient'];

    function PacientMedicalAnalysisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, PacientMedicalAnalysis, Medic, Pacient) {
        var vm = this;

        vm.pacientMedicalAnalysis = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.medics = Medic.query({filter: 'pacientmedicalanalysis-is-null'});
        $q.all([vm.pacientMedicalAnalysis.$promise, vm.medics.$promise]).then(function() {
            if (!vm.pacientMedicalAnalysis.medic || !vm.pacientMedicalAnalysis.medic.id) {
                return $q.reject();
            }
            return Medic.get({id : vm.pacientMedicalAnalysis.medic.id}).$promise;
        }).then(function(medic) {
            vm.medics.push(medic);
        });
        vm.pacients = Pacient.query({filter: 'pacientmedicalanalysis-is-null'});
        $q.all([vm.pacientMedicalAnalysis.$promise, vm.pacients.$promise]).then(function() {
            if (!vm.pacientMedicalAnalysis.pacient || !vm.pacientMedicalAnalysis.pacient.id) {
                return $q.reject();
            }
            return Pacient.get({id : vm.pacientMedicalAnalysis.pacient.id}).$promise;
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
            if (vm.pacientMedicalAnalysis.id !== null) {
                PacientMedicalAnalysis.update(vm.pacientMedicalAnalysis, onSaveSuccess, onSaveError);
            } else {
                PacientMedicalAnalysis.save(vm.pacientMedicalAnalysis, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:pacientMedicalAnalysisUpdate', result);
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
