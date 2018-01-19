(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicalAnalysisDialogController', MedicalAnalysisDialogController);

    MedicalAnalysisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'MedicalAnalysis', 'Medic', 'Pacient'];

    function MedicalAnalysisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, MedicalAnalysis, Medic, Pacient) {
        var vm = this;

        vm.medicalAnalysis = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.medics = Medic.query({filter: 'medicalanalysis-is-null'});
        $q.all([vm.medicalAnalysis.$promise, vm.medics.$promise]).then(function() {
            if (!vm.medicalAnalysis.medic || !vm.medicalAnalysis.medic.id) {
                return $q.reject();
            }
            return Medic.get({id : vm.medicalAnalysis.medic.id}).$promise;
        }).then(function(medic) {
            vm.medics.push(medic);
        });
        vm.pacients = Pacient.query({filter: 'medicalanalysis-is-null'});
        $q.all([vm.medicalAnalysis.$promise, vm.pacients.$promise]).then(function() {
            if (!vm.medicalAnalysis.pacient || !vm.medicalAnalysis.pacient.id) {
                return $q.reject();
            }
            return Pacient.get({id : vm.medicalAnalysis.pacient.id}).$promise;
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
            if (vm.medicalAnalysis.id !== null) {
                MedicalAnalysis.update(vm.medicalAnalysis, onSaveSuccess, onSaveError);
            } else {
                MedicalAnalysis.save(vm.medicalAnalysis, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:medicalAnalysisUpdate', result);
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
