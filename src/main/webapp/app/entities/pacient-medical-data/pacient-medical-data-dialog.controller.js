(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientMedicalDataDialogController', PacientMedicalDataDialogController);

    PacientMedicalDataDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'PacientMedicalData', 'Pacient', 'BloodGroup', 'Disabilities'];

    function PacientMedicalDataDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, PacientMedicalData, Pacient, BloodGroup, Disabilities) {
        var vm = this;

        vm.pacientMedicalData = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pacients = Pacient.query({filter: 'pacientmedicaldata-is-null'});
        $q.all([vm.pacientMedicalData.$promise, vm.pacients.$promise]).then(function() {
            if (!vm.pacientMedicalData.pacient || !vm.pacientMedicalData.pacient.id) {
                return $q.reject();
            }
            return Pacient.get({id : vm.pacientMedicalData.pacient.id}).$promise;
        }).then(function(pacient) {
            vm.pacients.push(pacient);
        });
        vm.bloodgroups = BloodGroup.query();
        vm.disabilities = Disabilities.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pacientMedicalData.id !== null) {
                PacientMedicalData.update(vm.pacientMedicalData, onSaveSuccess, onSaveError);
            } else {
                PacientMedicalData.save(vm.pacientMedicalData, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:pacientMedicalDataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
