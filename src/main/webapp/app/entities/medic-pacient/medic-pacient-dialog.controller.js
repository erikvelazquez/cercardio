(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicPacientDialogController', MedicPacientDialogController);

    MedicPacientDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'MedicPacient', 'Medic', 'Pacient'];

    function MedicPacientDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, MedicPacient, Medic, Pacient) {
        var vm = this;

        vm.medicPacient = entity;
        vm.clear = clear;
        vm.save = save;
        vm.medics = Medic.query({filter: 'medicpacient-is-null'});
        $q.all([vm.medicPacient.$promise, vm.medics.$promise]).then(function() {
            if (!vm.medicPacient.medic || !vm.medicPacient.medic.id) {
                return $q.reject();
            }
            return Medic.get({id : vm.medicPacient.medic.id}).$promise;
        }).then(function(medic) {
            vm.medics.push(medic);
        });
        vm.pacients = Pacient.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.medicPacient.id !== null) {
                MedicPacient.update(vm.medicPacient, onSaveSuccess, onSaveError);
            } else {
                MedicPacient.save(vm.medicPacient, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:medicPacientUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
